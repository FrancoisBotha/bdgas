// Courtesy of:
// https://gist.github.com/james2doyle/4aba55c22f084800c199

// usage: {{ file.size | prettyBytes }}
module.exports = {
    prettyBytes: (num) => {
        // jacked from: https://github.com/sindresorhus/pretty-bytes
        if (typeof num !== 'number' || isNaN(num)) {
            throw new TypeError('Expected a number');
        }
        
        var exponent;
        var unit;
        var neg = num < 0;
        var units = ['B', 'kB', 'MB', 'GB', 'TB', 'PB', 'EB', 'ZB', 'YB'];
        
        if (neg) {
            num = -num;
        }
        
        if (num < 1) {
            return (neg ? '-' : '') + num + ' B';
        }
        
        exponent = Math.min(Math.floor(Math.log(num) / Math.log(1000)), units.length - 1);
        num = (num / Math.pow(1000, exponent)).toFixed(2) * 1;
        unit = units[exponent];
        
        return (neg ? '-' : '') + num + ' ' + unit;
    }
}