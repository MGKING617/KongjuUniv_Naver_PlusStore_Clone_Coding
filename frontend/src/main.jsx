import React from 'react' // StrictMode 등은 React 객체에서 꺼내 쓸 수 있습니다.
import ReactDOM from 'react-dom/client'
import './index.css'
import App from './App.jsx' // 확장자 명시 혹은 ./App

ReactDOM.createRoot(document.getElementById('root')).render(
    <React.StrictMode>
        <App />
    </React.StrictMode>,
)