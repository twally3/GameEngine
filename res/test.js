const fs = require('fs');

const file = fs.readFileSync('bunny2.obj', 'utf8');
const lines = file
	.split('\n')
	.filter(line => line.match('f '))
	.map(line => {
		const items = line.split('/');
		items[1] = '1';
		items[3] = '2';
		items[5] = '3';
		return items.join('/');
	})
	.join('\n');

fs.writeFileSync('test', lines);