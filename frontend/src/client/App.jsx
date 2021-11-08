import React from "react";

import { BrowserRouter, Route, Switch } from "react-router-dom";
import Auth from "./components/Auth";
import Home from "./components/Home";
import Navbar from "./components/Navbar";
import Collection from "./components/Collection";

import { UserProvider } from "./contexts/UserContext";

const App = () => {
  return (
    <div>
      <BrowserRouter>
        <UserProvider>
          <Navbar />
          <Switch>
            <Route exact path={"/"}>
              <Home />
            </Route>
            <Route path={"/auth"}>
              <Auth />
            </Route>
            <Route path={"/collection"}>
              <Collection />
            </Route>
          </Switch>
        </UserProvider>
      </BrowserRouter>
    </div>
  );
};

export default App;
