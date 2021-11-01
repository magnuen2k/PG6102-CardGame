import React from "react";
import { Link } from "react-router-dom";

const Navbar = () => {
  return (
    <nav>
      <Link to={"/"}>Home</Link>
      <Link to={"/auth"}>Authenticate</Link>
      <Link>Logout</Link>
    </nav>
  );
};

export default Navbar;
