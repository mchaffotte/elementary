import { useMutation, gql } from "@apollo/client";
import { FunctionComponent, useEffect, useState } from "react";

import { Game } from "../api";
import { CharacterView } from "./CharacterView";
import { SectionView } from "./Section";

const TURN_TO = gql`
  mutation turnTo($id: Int!, $answer: String) {
    turnTo(index: $id, answer: $answer) {
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

type PlayGameProps = {
  initialGame: Game | null;
};

export const PlayGame: FunctionComponent<PlayGameProps> = ({ initialGame }) => {
  const [game, setGame] = useState<Game | null>(null);

  const [turnTo, response] =
    useMutation<{ turnTo: Game }, { id: number; answer: string }>(TURN_TO);

  useEffect(() => {
    if (initialGame) {
      setGame(initialGame);
    }
  }, [initialGame]);

  useEffect(() => {
    if (response.data) {
      setGame(response.data.turnTo);
    }
  }, [response]);

  const handleTurnTo = (index: number, answer: string) => {
    turnTo({ variables: { id: index, answer } });
  };

  return (
    <>
      <CharacterView character={game ? game.character : null} />
      <SectionView
        section={game ? game.section : null}
        onTurnTo={handleTurnTo}
      />
    </>
  );
};
