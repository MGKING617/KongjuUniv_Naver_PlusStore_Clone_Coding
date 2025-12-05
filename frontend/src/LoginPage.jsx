// LoginPage.js
import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';

function LoginPage() {
  const [id, setId] = useState('');
  const [pw, setPw] = useState('');
  const navigate = useNavigate();

  const onLogin = () => {
    if (id === "user" && pw === "1234") {
      alert("로그인 성공! 👏");
      navigate("/");
    } else {
      alert("아이디 또는 비밀번호가 틀렸습니다!");
    }
  };

  return (
    <div className="login-page">
      <h2>로그인</h2>
      <input
        type="text"
        placeholder="아이디"
        value={id}
        onChange={(e) => setId(e.target.value)}
      />
      <input
        type="password"
        placeholder="비밀번호"
        value={pw}
        onChange={(e) => setPw(e.target.value)}
      />
      <button onClick={onLogin}>로그인</button>
    </div>
  );
}

export default LoginPage;
