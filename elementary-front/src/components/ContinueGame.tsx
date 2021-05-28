import { useQuery, gql } from "@apollo/client";
import { FunctionComponent } from "react";
import { useHistory } from "react-router-dom";

import { PlayGame } from "./PlayGame";

const GET_GAME = gql`
  query getGame {
    game {
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
  const section = data?.game.section || null;

  return <PlayGame initialSection={section} />;
};
