import { useMutation, gql } from "@apollo/client";
import { FunctionComponent, useEffect } from "react";

import { Game } from "../api";
import { PlayGame } from "./PlayGame";

interface StartGameProps {
  storyId: number;
}

const START_GAME = gql`
  mutation startGame($id: BigInteger!) {
    stopGame
    startGame(storyId: $id) {
      section {
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

export const StartGame: FunctionComponent<StartGameProps> = ({ storyId }) => {
  const [start, response] =
    useMutation<{ startGame: Game }, { id: number }>(START_GAME);

  useEffect(() => {
    if (storyId) {
      start({ variables: { id: storyId } });
    }
  }, [storyId, start]);

  const section = response.data?.startGame.section || null;

  return <PlayGame initialSection={section} storyId={storyId} />;
};
