import { useMutation, gql } from "@apollo/client";
import { FunctionComponent, useEffect } from "react";

import { Game } from "../api";
import { PlayGame } from "./PlayGame";

type StartGameProps = {
  storyId: number;
  onStart: Function;
};

const START_GAME = gql`
  mutation startGame($id: BigInteger!) {
    stopGame
    startGame(storyId: $id) {
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

export const StartGame: FunctionComponent<StartGameProps> = ({
  storyId,
  onStart,
}) => {
  const [start, response] =
    useMutation<{ startGame: Game }, { id: number }>(START_GAME);

  useEffect(() => {
    if (storyId) {
      start({ variables: { id: storyId } });
    }
  }, [storyId, start]);

  const game = response.data?.startGame || null;
  if (game) {
    onStart();
  }

  return <PlayGame initialGame={game} />;
};
