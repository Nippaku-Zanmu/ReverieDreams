const https = require('https')
const readline = require('readline')
const fs = require('fs')

// 从 Mojang API 获取 JSON
function fetchJson(url) {
    return new Promise((resolve, reject) => {
        https.get(url, (res) => {
            if (res.statusCode !== 200) {
                reject(new Error(`请求失败：${res.statusCode}`))
                res.resume()
                return
            }

            let rawData = ''
            res.setEncoding('utf8')
            res.on('data', chunk => rawData += chunk)
            res.on('end', () => {
                try {
                    resolve(JSON.parse(rawData))
                } catch (e) {
                    reject(e)
                }
            })
        }).on('error', reject)
    })
}

// 下载皮肤图片
function downloadImage(url, outputPath) {
    https.get(url, (res) => {
        const fileStream = fs.createWriteStream(outputPath)
        res.pipe(fileStream)
        fileStream.on('finish', () => {
            console.log('皮肤图片下载完成:', outputPath)
        })
    })
}

// 主逻辑：传入用户名或 UUID，返回签名皮肤信息
async function getSignedSkin(usernameOrUuid) {
    let uuid = usernameOrUuid.trim()

    if (!/^[0-9a-f]{32}$/i.test(uuid)) {
        const uuidData = await fetchJson(`https://api.mojang.com/users/profiles/minecraft/${uuid}`)
        uuid = uuidData.id
    }

    const profileData = await fetchJson(`https://sessionserver.mojang.com/session/minecraft/profile/${uuid}?unsigned=false`)

    const textureProp = profileData.properties.find(p => p.name === 'textures')
    if (!textureProp) throw new Error('未找到皮肤属性')

    return {
        id: profileData.id,
        name: profileData.name,
        value: textureProp.value,
        signature: textureProp.signature,
        skinUrl: (() => {
            try {
                const decoded = JSON.parse(Buffer.from(textureProp.value, 'base64').toString())
                return decoded.textures.SKIN.url
            } catch {
                return null
            }
        })(),
    }
}

// 控制台交互循环
const rl = readline.createInterface({
    input: process.stdin,
    output: process.stdout,
})

console.log('请输入 Minecraft 玩家名或 UUID，输入 "exit" 退出。')

function prompt() {
    rl.question('> ', async (input) => {
        if (input.toLowerCase() === 'exit') {
            rl.close()
            return
        }

        try {
            const result = await getSignedSkin(input)
            console.log('--- 获取成功 ---')
            console.log('UUID:', result.id)
            console.log('Name:', result.name)
            console.log('Skin URL:', result.skinUrl)
            console.log('Value:', result.value)  // 输出完整 value
            console.log('Signature:', result.signature)  // 输出完整 signature

            if (result.skinUrl) {
                // downloadImage(result.skinUrl, `${result.name}_skin.png`)
            }
        } catch (err) {
            // console.error('获取失败:', err.message)
        }

        prompt()
    })
}

prompt()
