const fs = require('fs');
const path = require('path');

// 递归获取所有 json 文件路径
function findJsonFiles(dir) {
  let results = [];
  const list = fs.readdirSync(dir);

  for (const file of list) {
    const fullPath = path.join(dir, file);
    const stat = fs.statSync(fullPath);

    if (stat && stat.isDirectory()) {
      results = results.concat(findJsonFiles(fullPath));
    } else if (file.endsWith('.json')) {
      results.push(fullPath);
    }
  }

  return results;
}

// 修复 textures 字段
function fixTextures(filePath) {
  try {
    const data = JSON.parse(fs.readFileSync(filePath, 'utf-8'));

    if (typeof data.textures === 'object' && data.textures !== null) {
      let modified = false;

      if (data.textures.missing === undefined) {
        data.textures.missing = 'reverie_dreams:block/fumo/missing';
        modified = true;
      }

      if (data.textures.particle === undefined) {
        data.textures.particle = 'reverie_dreams:block/fumo/missing';
        modified = true;
      }

      if (modified) {
        fs.writeFileSync(filePath, JSON.stringify(data, null, 2));
        console.log(`✔ Patched: ${filePath}`);
      }
    }
  } catch (err) {
    console.warn(`✘ Failed to process ${filePath}: ${err.message}`);
  }
}

// 主流程
const root = process.cwd();
const jsonFiles = findJsonFiles(root);

jsonFiles.forEach(fixTextures);
