import { useQuery, gql } from "@apollo/client";
import { FunctionComponent } from "react";
import { useHistory } from "react-router-dom";

import { PlayGame } from "./PlayGame";

const GET_GAME = gql`
  query getGame {
    game {
      character {
        name
        money {
          pounds
          shillings
          pence
        }
        skills {
          name
          value
        }
      }
      section {
        storyId
        reference
        text
        actions {
          description
          answerNeeded
        }
      }
    }
  }
`;

export const ContinueGame: FunctionComponent<{}> = () => {
  const history = useHistory();
  const { error, data } = useQuery(GET_GAME);

  if (error) {
    history.push("/");
  }
  const game = data?.game || null;

  return <PlayGame initialGame={game} />;
};
