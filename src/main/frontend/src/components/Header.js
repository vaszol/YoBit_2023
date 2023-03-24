import React from 'react';

function Header() {
    return (
        <header>
            <div>
                <span className="logo">YoBit 2023</span>
                <ul className="nav">
                    <li>Про нас</li>
                    <li>Контакты</li>
                    <li>Кабинет</li>
                </ul>
            </div>
            <div className="presentation"></div>
        </header>
    );
};

export default Header;
