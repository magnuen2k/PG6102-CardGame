import React, { useState, useEffect } from "react";
import axios from "axios";

const Collection = () => {
  const [collection, setCollection] = useState([]);

  useEffect(() => {
    fetchCollection();
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

  if (!collection.cards) {
    return <div>Loading...</div>;
  }

  return (
    <div className={"container"}>
      <h1>Collection</h1>
      <div className="card-container">
        {collection.cards.map((c) => (
          <div key={c.cardId}>
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
