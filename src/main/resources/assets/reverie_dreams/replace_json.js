const fs = require('fs');
const path = require('path');

// 设置当前目录为根目录进行扫描
const rootDir = path.join(__dirname);

// 递归读取目录及其子目录中的所有文件
function scanDirectory(directory) {
    fs.readdir(directory, (err, files) => {
        if (err) {
            console.error(`无法读取目录 ${directory}:`, err);
            return;
        }

        files.forEach(file => {
            const filePath = path.join(directory, file);

            // 检查是否是目录，递归扫描
            if (fs.lstatSync(filePath).isDirectory()) {
                scanDirectory(filePath);
            } else {
                // 只处理.json文件
                if (filePath.endsWith('.json')) {
                    processFile(filePath);
                }
            }
        });
    });
}

// 处理.json文件内容
function processFile(filePath) {
    fs.readFile(filePath, 'utf8', (err, data) => {
        if (err) {
            console.error(`读取文件失败 ${filePath}:`, err);
            return;
        }

        // 替换文本中的内容
        const updatedData = data.replace(/polymerized-touhou-mod/g, 'reverie_dreams');

        // 写回更新后的内容
        fs.writeFile(filePath, updatedData, 'utf8', (err) => {
            if (err) {
                console.error(`写入文件失败 ${filePath}:`, err);
            } else {
                console.log(`成功更新文件: ${filePath}`);
            }
        });
    });
}

// 从当前目录开始递归扫描
scanDirectory(rootDir);
