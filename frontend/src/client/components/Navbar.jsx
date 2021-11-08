import React, { useContext } from "react";
import { Link, useHistory } from "react-router-dom";
import axios from "axios";
import { UserContext } from "../contexts/UserContext";

const Navbar = () => {
  const history = useHistory();

  const { user, setUser } = useContext(UserContext);

  const logout = async () => {
    if (user) {
      const res = await axios.post("/api/auth/logout");
      console.log(res);
      history.push("/");
      setUser(null);
    }
  };

  return (
    <nav style={{ display: "flex", justifyContent: "center" }}>
      <div className={"nav-element"}>
        <Link to={"/"}>Home</Link>
      </div>
      <div className={"nav-element"}>
        <Link to={"/auth"}>Authenticate</Link>
      </div>
      <div className={"nav-element"}>
        <Link to={"/collection"}>Collection</Link>
      </div>
      <div className={"nav-element"} onClick={logout}>
        Logout
      </div>
    </nav>
  );
};

export default Navbar;
