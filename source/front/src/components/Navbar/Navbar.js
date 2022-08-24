import i18next from "i18next";

// React-BootStrap
import Container from 'react-bootstrap/Container';
import Nav from 'react-bootstrap/Nav';
import Navbar from 'react-bootstrap/Navbar';
import { useLocation} from 'react-router-dom';

const NavbarTemp = () => {
    const {pathname} = useLocation();
    const handleLangChange=(e)=>{
        const lang = e.target.value;
        i18next.changeLanguage(lang);
    }
  return (
    <Navbar bg="dark" variant="dark">
        <Container>
            <Navbar.Brand className="text-success ms-5" style={{fontSize:"2rem",fontWeight:"bold" }} href="/">
                LINE
            </Navbar.Brand>
            <Nav className="d-flex flex-column">
                <Container className='d-flex'>
                    <Nav.Link className={pathname==="/"?"text-white":null} href="/">Home</Nav.Link>
                    <Nav.Link className={pathname==="/features"?"text-white":null} href="features">Features</Nav.Link>
                </Container>
            </Nav>

        </Container>
        <div>
            <p className="text-muted mb-0" >Navbar 는 개발 환경에서 사용합니다. 
                <span style={{fontSize:11 }}>
                (추후 삭제 예정.)
                </span>
            </p>
            
            {/* Lang Service */}
            <div className='d-flex' >
                <h6 className='text-white me-3' >Languge</h6>
                <select onChange={handleLangChange} name="lang" id="lang">
                    <option value="en">English</option>
                    <option value="ko">한국어</option>
                    <option value="jp">日本語</option>
                </select>
            </div>
        </div>

    </Navbar>
  )
}

export default NavbarTemp