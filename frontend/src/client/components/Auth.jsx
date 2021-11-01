import React, { useState, useContext } from "react";
import axios from "axios";
import { UserContext } from "../contexts/UserContext";
import { useHistory } from "react-router";

const Auth = () => {
  const { user, setUser } = useContext(UserContext);
  const [formData, setFormData] = useState({
    userId: "",
    password: "",
  });
  const [isSignup, setIsSignup] = useState(false);

  const history = useHistory();

  const handleChange = (e) => {
    setFormData({
      ...formData,
      [e.target.name]: e.target.value,
    });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (isSignup) {
      // Handle signup
      await doSignUp();
    } else {
      // Handle signin
      await doSignIn();
    }
  };

  const doSignUp = async (e) => {
    try {
      const res = await axios.post("/api/auth/signUp", formData);

      console.log(res.status);

      if (res.status === 201) {
        const user = await axios.get("api/auth/user");
        console.log(user.data);
        setUser(user.data);
        history.push("/");
      }

      console.log(res);
    } catch (e) {
      console.log(e);
    }
  };

  const doSignIn = async () => {
    try {
      const res = await axios.post("/api/auth/login", formData);

      console.log(res);

      if (res.status === 204) {
        const user = await axios.get("api/auth/user");
        console.log(user.data);
        setUser(user.data);
        history.push("/");
      }
    } catch (e) {
      console.log(e);
    }
  };

  const switchMode = () => {
    setIsSignup((prevIsSignup) => !prevIsSignup);
    // Find a way to clear input fields
  };

  return (
    <div className={"container"}>
      <h1>Authentication</h1>
      {user ? (
        <div>Already logged in</div>
      ) : (
        <>
          <h1>{isSignup ? "Sign up" : "Login"}</h1>

          <form onSubmit={handleSubmit}>
            <input type={"text"} name={"userId"} onChange={handleChange} />
            <input
              type={"password"}
              name={"password"}
              onChange={handleChange}
            />
            <button>Submit</button>
          </form>
          <button id="switch" onClick={switchMode}>
            {isSignup
              ? "Already have an account? Sign In"
              : "Dont have an account yet? Sign Up"}
          </button>
        </>
      )}
    </div>
  );
};

export default Auth;
