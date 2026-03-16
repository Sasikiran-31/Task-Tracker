import NavigationBar from "./NavigationBar.jsx";
import {Outlet} from "react-router-dom";


const Layout = () => {
    return (
        <>
        <NavigationBar />
        <main>
            <Outlet />
        </main>
        </>
    );
}

export default Layout;