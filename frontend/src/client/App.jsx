import React from "react";

import { BrowserRouter, Route, Switch } from "react-router-dom";
import Auth from "./components/Auth";
import Home from "./components/Home";
import { UserProvider } from "./contexts/UserContext";
import Navbar from "./components/Navbar";

const App = () => {
  return (
    <div>
      <BrowserRouter>
        <Navbar />

        <UserProvider>
          <Switch>
            <Route exact path={"/"}>
              <Home />
            </Route>
            <Route path={"/auth"}>
              <Auth />
            </Route>
          </Switch>
        </UserProvider>
      </BrowserRouter>
    </div>
  );
};

export default App;
