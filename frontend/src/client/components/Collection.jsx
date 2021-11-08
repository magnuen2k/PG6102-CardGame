import React, { useContext, useState, useEffect } from "react";
import axios from "axios";
import { UserContext } from "../contexts/UserContext";
import { useHistory } from "react-router";

const Collection = () => {
  const [collection, setCollection] = useState([]);
  const [userStats, setUserStats] = useState([]);

  const { user, setUser } = useContext(UserContext);

  const history = useHistory();

  useEffect(() => {
    fetchCollection();
    fetchUserStats();
  }, []);

  const fetchCollection = async () => {
    const url = "/api/cards/collection_v1_000";

    let response;

    try {
      response = await axios.get(url);
    } catch (e) {
      console.log(e);
    }

    if (response.status !== 200) {
      console.log("something is wrong...");
    }

    const payload = await response.data.data;

    console.log(payload.cards);

    setCollection(payload);
  };

  const fetchUserStats = async () => {
    const url = "/api/user-collections/" + user.name;
    let res;

    try {
      res = await axios.get(url);
    } catch (e) {
      console.log(e);
    }

    if (res.status === 401) {
      setUser(null);
      history.push("/");
    }

    if (res.status === 404) {
      console.log("Something went wrong");
    }

    if (res.status !== 200) {
      console.log("Something went wrong");
    }

    const payload = await res.data.data;
    console.log(payload);
    setUserStats(payload);
  };

  if (!user) {
    return <div>Not logged in</div>;
  }

  if (!collection.cards || !userStats) {
    return <div>Loading...</div>;
  }

  return (
    <div className={"container"}>
      <h1>Collection</h1>
      <div>
        <p>Coins: {userStats.coins}</p>
        <p>Packs: {userStats.cardPacks}</p>
      </div>
      <div className="card-container">
        {collection.cards.map((c) => (
          <div className={"card " + c.rarity} key={c.cardId}>
            <div>{c.name}</div>
            <img
              style={{ width: "10rem" }}
              src={"/api/cards/imgs/" + c.imageId}
              alt={"image"}
            />
          </div>
        ))}
      </div>
    </div>
  );
};

export default Collection;
