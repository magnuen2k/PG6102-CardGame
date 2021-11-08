import React, { useContext } from "react";
import { UserContext } from "../contexts/UserContext";

const Home = () => {
  const { user } = useContext(UserContext);

  return (
    <div className={"container"}>
      <h1>Home</h1>
      {user ? <div>Welcome, {user.name}</div> : <div>Not logged in</div>}
    </div>
  );
};

export default Home;
