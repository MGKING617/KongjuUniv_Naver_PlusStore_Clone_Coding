import React, { useState, useEffect, useContext } from 'react';
import { useNavigate } from 'react-router-dom';
import { AuthContext } from './AuthContext';

function SellerPage() {
    const { user, isLoading } = useContext(AuthContext);
    const navigate = useNavigate();

    const [name, setName] = useState('');
    const [price, setPrice] = useState('');
    const [stock, setStock] = useState('');
    const [imageFile, setImageFile] = useState(null);

    const [categories, setCategories] = useState([]);
    const [selectedCategoryId, setSelectedCategoryId] = useState('');
    const [isNewCategoryMode, setIsNewCategoryMode] = useState(false);
    const [newCategoryName, setNewCategoryName] = useState('');

    const [options, setOptions] = useState([{ optionName: '기본', extraPrice: 0, stock: 10 }]);
    const [myProducts, setMyProducts] = useState([]);

    useEffect(() => {
        if (!isLoading) {
            if (!user) {
                alert("판매자 로그인이 필요합니다.");
                navigate('/login');
                return;
            }
            if (user.role !== 'SELLER') {
                alert("판매자 회원만 접근할 수 있습니다.\n판매자 계정으로 로그인해주세요.");
                navigate('/login');
                return;
            }

            fetch(`http://localhost:8080/api/seller/categories/${user.id}`).then(res=>res.json()).then(setCategories);
            fetch(`http://localhost:8080/api/seller/products/${user.id}`).then(res=>res.json()).then(setMyProducts);
        }
    }, [user, isLoading, navigate]);


    const handleDeleteProduct = (productId) => {
        if (window.confirm("정말로 이 상품을 삭제하시겠습니까?")) {
            fetch(`http://localhost:8080/api/seller/products/${productId}`, {
                method: 'DELETE',
            })
                .then(res => {
                    if (res.ok) {
                        alert("상품이 삭제되었습니다.");

                        setMyProducts(myProducts.filter(p => p.productId !== productId));
                    } else {
                        alert("삭제 실패");
                    }
                })
                .catch(err => console.error("삭제 에러:", err));
        }
    };

    const addOptionField = () => setOptions([...options, { optionName: '', extraPrice: 0, stock: 0 }]);

    const handleOptionChange = (index, field, value) => {
        const newOptions = [...options];
        newOptions[index][field] = value;
        setOptions(newOptions);
    };

    const removeOption = (index) => {
        if(options.length === 1) return alert("최소 1개 필수");
        setOptions(options.filter((_, i) => i !== index));
    };

    const handleSubmit = () => {
        if (!name || !price) return alert("상품명/가격 필수");
        if (!isNewCategoryMode && !selectedCategoryId) return alert("카테고리 선택 필수");

        const productData = {
            sellerId: user.id,
            name,
            price: parseInt(price),
            stock: parseInt(stock) || 0,
            categoryId: isNewCategoryMode ? null : selectedCategoryId,
            newCategoryName: isNewCategoryMode ? newCategoryName : null,
            options: options.map(opt => ({
                optionName: opt.optionName,
                extraPrice: parseInt(opt.extraPrice) || 0,
                stock: parseInt(opt.stock) || 0
            }))
        };

        const formData = new FormData();
        formData.append("productData", JSON.stringify(productData));
        if (imageFile) {
            formData.append("image", imageFile);
        }

        fetch('http://localhost:8080/api/seller/products', {
            method: 'POST',
            body: formData
        }).then(res => {
            if (res.ok) {
                alert("등록 성공!");
                window.location.reload();
            } else alert("등록 실패");
        });
    };

    if (isLoading || !user || user.role !== 'SELLER') return null;

    return (
        <div style={{ maxWidth: '800px', margin: '20px auto', padding:'20px' }}>
            <h2>📢 판매자 센터 ({user.name}님)</h2>
            <div style={{ padding: '25px', background: '#f8f9fa', borderRadius: '10px', border:'1px solid #ddd' }}>


                <div style={{marginBottom:'20px'}}>
                    <label style={{fontWeight:'bold', display:'block', marginBottom:'5px'}}>카테고리</label>
                    <div style={{display:'flex', gap:'10px'}}>
                        {!isNewCategoryMode ? (
                            <select style={{flex:1, padding:'10px'}} value={selectedCategoryId} onChange={e => setSelectedCategoryId(e.target.value)}>
                                <option value="">-- 선택 --</option>
                                {categories.map(c => <option key={c.categoryId} value={c.categoryId}>{c.categoryName}</option>)}
                            </select>
                        ) : (
                            <input style={{flex:1, padding:'10px'}} placeholder="새 카테고리명" value={newCategoryName} onChange={e => setNewCategoryName(e.target.value)} />
                        )}
                        <button onClick={() => setIsNewCategoryMode(!isNewCategoryMode)} style={{padding:'10px', background:'#555', color:'white', border:'none', borderRadius:'4px', cursor:'pointer'}}>
                            {isNewCategoryMode ? "목록 선택" : "새로 만들기"}
                        </button>
                    </div>
                </div>


                <label style={{fontWeight:'bold', display:'block', marginTop:'15px'}}>상품 정보</label>
                <input placeholder="상품명" value={name} onChange={e => setName(e.target.value)} style={inputStyle}/>
                <input placeholder="기본 가격 (숫자만 입력)" type="number" value={price} onChange={e => setPrice(e.target.value)} style={inputStyle}/>

                <div style={{marginBottom:'15px'}}>
                    <label style={{fontWeight:'bold', display:'block', marginBottom:'5px'}}>상품 이미지</label>
                    <input type="file" onChange={e => setImageFile(e.target.files[0])} style={inputStyle} accept="image/*" />
                </div>

                {/* 3. 옵션 관리 */}
                <div style={{marginTop:'30px', padding:'15px', background:'white', borderRadius:'8px', border:'1px solid #ccc'}}>
                    <div style={{display:'flex', justifyContent:'space-between', marginBottom:'10px'}}>
                        <label style={{fontWeight:'bold', color:'#03c75a'}}>옵션 구성</label>
                        <button onClick={addOptionField} style={{background:'#03c75a', color:'white', border:'none', padding:'5px 10px', borderRadius:'4px', cursor:'pointer'}}>+ 옵션 추가</button>
                    </div>

                    <div style={{display:'flex', gap:'5px', marginBottom:'5px', padding:'0 10px', fontSize:'13px', fontWeight:'bold', color:'#555'}}>
                        <div style={{flex:2}}>옵션 이름 (예: 빨강, XL)</div>
                        <div style={{flex:1}}>추가금 (+/-)</div>
                        <div style={{flex:1}}>재고 수량</div>
                        <div style={{width:'30px'}}></div>
                    </div>

                    {options.map((opt, idx) => (
                        <div key={idx} style={{display:'flex', gap:'5px', marginBottom:'10px'}}>
                            <input placeholder="옵션명" value={opt.optionName} onChange={e => handleOptionChange(idx, 'optionName', e.target.value)} style={{flex:2, padding:'8px', border:'1px solid #ddd', borderRadius:'4px'}} />
                            <input placeholder="추가금(원)" type="number" value={opt.extraPrice} onChange={e => handleOptionChange(idx, 'extraPrice', e.target.value)} style={{flex:1, padding:'8px', border:'1px solid #ddd', borderRadius:'4px'}} />
                            <input placeholder="수량(개)" type="number" value={opt.stock} onChange={e => handleOptionChange(idx, 'stock', e.target.value)} style={{flex:1, padding:'8px', border:'1px solid #ddd', borderRadius:'4px'}} />
                            <button onClick={() => removeOption(idx)} style={{background:'#ff4d4f', color:'white', border:'none', borderRadius:'4px', cursor:'pointer', padding:'0 10px'}}>X</button>
                        </div>
                    ))}
                </div>

                <button onClick={handleSubmit} style={{ width:'100%', marginTop:'20px', padding:'15px', background:'#03c75a', color:'white', border:'none', fontWeight:'bold', borderRadius:'5px', cursor:'pointer'}}>등록하기</button>
            </div>

            <h3 style={{marginTop:'40px'}}>📦 등록된 상품</h3>
            <ul style={{listStyle:'none', padding:0}}>
                {myProducts.map(p => (
                    <li key={p.productId} style={{borderBottom:'1px solid #eee', padding:'15px 0', display:'flex', justifyContent:'space-between', alignItems:'center'}}>
                        <div style={{display:'flex', alignItems:'center', gap:'15px'}}>
                            <img src={p.imageUrl || "https://via.placeholder.com/50"} style={{width:'50px', height:'50px', objectFit:'cover', borderRadius:'4px'}} />
                            <div>
                                <span style={{fontSize:'12px', color:'#03c75a', border:'1px solid #03c75a', padding:'2px 6px', borderRadius:'10px', marginRight:'8px'}}>
                                    {p.category ? p.category.categoryName : '미지정'}
                                </span>
                                <strong>{p.name}</strong>
                            </div>
                        </div>
                        <div style={{display:'flex', alignItems:'center', gap:'20px'}}>
                            <span style={{fontWeight:'bold'}}>{p.price.toLocaleString()}원</span>
                            {/* 🟢 삭제 버튼 추가 */}
                            <button
                                onClick={() => handleDeleteProduct(p.productId)}
                                style={{background:'#ff4d4f', color:'white', border:'none', padding:'8px 12px', borderRadius:'4px', cursor:'pointer', fontWeight:'bold'}}
                            >
                                삭제
                            </button>
                        </div>
                    </li>
                ))}
            </ul>
        </div>
    );
}
const inputStyle = { width: '100%', padding: '10px', marginBottom: '10px', border: '1px solid #ddd', borderRadius: '4px', boxSizing:'border-box' };
export default SellerPage;