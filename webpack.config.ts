import * as webpack from 'webpack';
import * as path from 'path';
import * as glob from 'glob';

const MiniCssExtractPlugin = require("mini-css-extract-plugin");

type Entry = {
    key: string,
    value: string
};

const scriptEntries = (baseDir: string) => {
    const filePaths: Array<string> = glob.sync(`${baseDir}/**/*.ts`)

    const entries: Entry[] = filePaths.map((filePath: string) => {
        const key: string = filePath.split('/').pop().replace('.ts', '')
        return {
            key: key,
            value: filePath
        }
    })

    return entries.reduce((acc, entry) => ({ ...acc, ...{ [entry.key]: entry.value }}), {})
};

const entries = {
    ...scriptEntries('./src/main/resources/js/entries'),
    style: './src/main/resources/styles/style.scss'
};

const output = {
    filename: 'js/[name].bundle.js',
    path: path.resolve(__dirname, './src/main/resources/static')
};

const rules = [
    {
        test: /\.(ts|tsx)$/,
        use: 'ts-loader'
    },
    {
        test: /\.(scss|sass)$/,
        use: [
            { loader: MiniCssExtractPlugin.loader },
            { loader: 'css-loader' },
            { loader: 'sass-loader' }
        ]
    }
];

const plugins = [
    new MiniCssExtractPlugin(
        {
            filename: 'css/[name].css'
        }
    )
];

const resolve = {
    extensions: [
        '.js',
        '.ts',
        '.css',
        '.scss',
        '.sass'
    ],
};

const config: webpack.Configuration[] = [
    {
        entry: entries,
        output: output,
        module: { rules },
        plugins: plugins,
        resolve: resolve
    }
];

export default config;
