import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';

function SignupPage() {
    const navigate = useNavigate();


    const [userType, setUserType] = useState('BUYER');


    const [formData, setFormData] = useState({
        loginId: '',
        password: '',
        name: '',
        email: '',
        phone: '',
        address: '',
        storeName: '',
        phoneNumber: ''
    });

    const handleChange = (e) => {
        setFormData({ ...formData, [e.target.name]: e.target.value });
    };

    const handleSignup = () => {

        let url = "";
        let bodyData = {};

        if (userType === 'BUYER') {
            url = "http://localhost:8080/api/customers/register";
            bodyData = {
                loginId: formData.loginId,
                password: formData.password,
                name: formData.name,
                email: formData.email,
                phone: formData.phone,
                address: formData.address
            };
        } else {
            url = "http://localhost:8080/api/sellers/register";
            bodyData = {
                loginId: formData.loginId,
                password: formData.password,
                storeName: formData.storeName,
                phoneNumber: formData.phoneNumber
            };
        }

        fetch(url, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(bodyData)
        })
            .then(res => {
                if (res.ok) {
                    alert("가입이 완료되었습니다! 로그인 해주세요.");
                    navigate("/login");
                } else {
                    alert("가입 실패: 이미 사용 중인 아이디거나 정보가 부족합니다.");
                }
            })
            .catch(err => console.error("Error:", err));
    };


    const containerStyle = {
        maxWidth: '460px',
        margin: '50px auto',
        padding: '20px',
        fontFamily: "'Noto Sans KR', sans-serif"
    };

    const headerStyle = {
        textAlign: 'center',
        marginBottom: '30px',
        fontSize: '32px',
        fontWeight: 'bold',
        color: '#03c75a'
    };

    const tabContainerStyle = {
        display: 'flex',
        marginBottom: '20px',
        border: '1px solid #ddd',
        borderRadius: '6px',
        overflow: 'hidden'
    };

    const tabStyle = (isActive) => ({
        flex: 1,
        padding: '14px',
        textAlign: 'center',
        cursor: 'pointer',
        background: isActive ? '#03c75a' : '#fff',
        color: isActive ? '#fff' : '#888',
        fontWeight: 'bold',
        fontSize: '16px',
        transition: 'all 0.2s'
    });

    const inputStyle = {
        width: '100%',
        padding: '14px',
        marginBottom: '12px',
        border: '1px solid #dadada',
        borderRadius: '6px',
        fontSize: '15px',
        boxSizing: 'border-box'
    };

    const buttonStyle = {
        width: '100%',
        padding: '16px',
        marginTop: '10px',
        background: '#03c75a',
        color: 'white',
        border: 'none',
        fontSize: '18px',
        fontWeight: 'bold',
        borderRadius: '6px',
        cursor: 'pointer'
    };

    return (
        <div style={containerStyle}>
            <h2 style={headerStyle}>회원가입</h2>


            <div style={tabContainerStyle}>
                <div style={tabStyle(userType === 'BUYER')} onClick={() => setUserType('BUYER')}>
                    개인 구매회원
                </div>
                <div style={tabStyle(userType === 'SELLER')} onClick={() => setUserType('SELLER')}>
                    판매자 회원
                </div>
            </div>


            <div style={{ display: 'flex', flexDirection: 'column' }}>


                <input style={inputStyle} name="loginId" placeholder="아이디" onChange={handleChange} />
                <input style={inputStyle} name="password" type="password" placeholder="비밀번호" onChange={handleChange} />


                {userType === 'BUYER' && (
                    <>
                        <input style={inputStyle} name="name" placeholder="이름" onChange={handleChange} />
                        <input style={inputStyle} name="email" placeholder="이메일 (example@naver.com)" onChange={handleChange} />
                        <input style={inputStyle} name="phone" placeholder="휴대전화번호" onChange={handleChange} />
                        <input style={inputStyle} name="address" placeholder="주소" onChange={handleChange} />
                    </>
                )}


                {userType === 'SELLER' && (
                    <>
                        <input style={inputStyle} name="storeName" placeholder="스토어 이름 (예: 홍길동 마켓)" onChange={handleChange} />
                        <input style={inputStyle} name="phoneNumber" placeholder="고객센터 번호 (010-0000-0000)" onChange={handleChange} />
                        <p style={{fontSize:'12px', color:'#888', marginTop:'-5px', marginLeft:'5px'}}>
                            * 판매자 가입 시 사업자 등록증 검토 과정은 생략됩니다.
                        </p>
                    </>
                )}

                <button style={buttonStyle} onClick={handleSignup}>
                    가입하기
                </button>
            </div>
        </div>
    );
}

export default SignupPage;