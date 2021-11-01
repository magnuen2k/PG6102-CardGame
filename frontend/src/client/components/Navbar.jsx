import React from "react";
import { Link } from "react-router-dom";

const Navbar = () => {
  const logout = () => {
    console.log("Should logout");
  };

  return (
    <nav style={{ display: "flex", justifyContent: "center" }}>
      <div className={"nav-element"}>
        <Link to={"/"}>Home</Link>
      </div>
      <div className={"nav-element"}>
        <Link to={"/auth"}>Authenticate</Link>
      </div>
      <div className={"nav-element"} onClick={logout}>
        Logout
      </div>
    </nav>
  );
};

export default Navbar;
