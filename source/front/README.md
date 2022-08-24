# Feature Store Frontend

This project was bootstrapped with [Create React App](https://github.com/facebook/create-react-app).

---

### Work Journey📌

#### 📍Todo Task📍

1. Exclude statement
2. AgGrid Pagination from outside.

#### ⛔️Known Issue⛔️

1. Chnage Dummy Data

---

#### 🛑 Dev History

###### `1. Bootstrap Css Warning`

- Warning
  added this code inside package.json to avoid Warning
  `"overrides": { "autoprefixer": "10.4.5" }`

  ```
  Warning
  (6:29521) autoprefixer: Replace color-adjust to print-color-adjust. The color-adjust shorthand is currently deprecated.
  Search for the keywords to learn more about each warning.
  To ignore, add // eslint-disable-next-line to the line before.

  WARNING in ./node_modules/bootstrap/dist/css/bootstrap.min.css (./node_modules/css-loader/dist/cjs.js??ruleSet[1].rules[1].oneOf[5].use[1]!./node_modules/postcss-loader/dist/cjs.js??ruleSet[1].rules[1].oneOf[5].use[2]!./node_modules/source-map-loader/dist/cjs.js!./node_modules/bootstrap/dist/css/bootstrap.min.css)
  Module Warning (from ./node_modules/postcss-loader/dist/cjs.js):
  ```

- solution
  Add this to Package.json
  `"overrides": { "autoprefixer": "10.4.5" }`
  [Bootstrap css warning Stackoverflow](https://stackoverflow.com/questions/72108482/reactjs-compiled-with-warning)

###### `2. Table Library Ag-grid`

[Ag-Grid](https://www.ag-grid.com/)

```js
/**
 *  their versions must match.
 * */
'ag-grid-community'; // core of [ag-grid]
'ag-grid-react'; // rendering part of [ag-grid]
```

- Ag-Grid Style Issues

```js
<div className="ag-theme-alpine" style={{ height: '50vh', margin: '0 2rem' }}>
  <AgGridReact
    onCellDoubleClicked={handleDobuleClick}
    onCellClicked={handleCellClick}
    rowData={rowData}
    columnDefs={columnDefs}
    defaultColDef={defaultColDef}
    animateRows={true}
    pagination={true}
    paginationPageSize={10}
  />
</div>
```

#### 3. i18n Internation Languages Supports
[Site Link](https://www.i18next.com/)