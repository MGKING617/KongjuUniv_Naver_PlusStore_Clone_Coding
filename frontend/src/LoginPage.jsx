import React, { useState, useContext } from 'react';
import { useNavigate, Link } from 'react-router-dom';
import { AuthContext } from './AuthContext';

function LoginPage() {
    const [loginId, setLoginId] = useState('');
    const [password, setPassword] = useState('');
    const [role, setRole] = useState('BUYER');

    const navigate = useNavigate();
    const { login } = useContext(AuthContext);

    const onLogin = () => {
        if (!loginId || !password) {
            alert("아이디와 비밀번호를 입력해주세요.");
            return;
        }

        const endpoint = role === 'SELLER'
            ? "http://localhost:8080/api/sellers/login"
            : "http://localhost:8080/api/customers/login";

        fetch(endpoint, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ loginId, password })
        })
            .then(res => {
                if (res.ok) return res.json();
                throw new Error("로그인 실패");
            })
            .then(data => {

                const userData = {
                    id: role === 'SELLER' ? data.sellerId : data.customerId,
                    name: role === 'SELLER' ? data.storeName : data.name,
                    role: role,
                    loginId: data.loginId
                };

                alert(`${userData.name}님 환영합니다!`);
                login(userData);
                navigate(role === 'SELLER' ? "/seller" : "/");
            })
            .catch(err => {
                console.error(err);
                alert("아이디 또는 비밀번호가 일치하지 않습니다.");
            });
    };

    return (
        <div className="login-page">
            <h2 style={{color: '#03c75a'}}>N PlusStore 로그인</h2>

            <div style={{display:'flex', marginBottom:'20px', border:'1px solid #ddd', borderRadius:'6px'}}>
                <button
                    onClick={() => setRole('BUYER')}
                    style={{
                        flex:1, padding:'10px', border:'none',
                        background: role === 'BUYER' ? '#03c75a' : 'white',
                        color: role === 'BUYER' ? 'white' : '#555',
                        fontWeight: 'bold', cursor:'pointer'
                    }}>
                    개인 구매회원
                </button>
                <button
                    onClick={() => setRole('SELLER')}
                    style={{
                        flex:1, padding:'10px', border:'none',
                        background: role === 'SELLER' ? '#03c75a' : 'white',
                        color: role === 'SELLER' ? 'white' : '#555',
                        fontWeight: 'bold', cursor:'pointer'
                    }}>
                    판매자 회원
                </button>
            </div>

            <input type="text" placeholder="아이디" value={loginId} onChange={(e) => setLoginId(e.target.value)} />
            <input type="password" placeholder="비밀번호" value={password} onChange={(e) => setPassword(e.target.value)} />

            <button onClick={onLogin} style={{marginTop:'10px'}}>로그인</button>

            <div style={{ marginTop: '20px' }}>
                <Link to="/signup" style={{ color: '#03c75a', textDecoration: 'none', fontSize:'14px' }}>회원가입</Link>
            </div>
        </div>
    );
}

export default LoginPage;