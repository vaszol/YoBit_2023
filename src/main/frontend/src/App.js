import Header from "./components/Header";
import Footer from "./components/Footer";
import DepthApi from "./components/DepthApi";

function App() {
    return (
        <div className="wrapper">
            <Header/>
            <DepthApi/>
            <Footer/>
        </div>
    );
}

export default App;
