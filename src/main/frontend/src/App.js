import Header from "./components/Header";
import Footer from "./components/Footer";
import DepthApi from "./components/DepthApi";
import InfoApi from "./components/InfoApi";
import {Stack} from "@mui/material";

function App() {
    return (
        <div className="wrapper">
            <Header/>
            <Stack direction="row" spacing={2}>
                <DepthApi/>
                <InfoApi/>
            </Stack>
            <Footer/>
        </div>
    );
}

export default App;
