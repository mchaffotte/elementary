import { useMutation, gql } from "@apollo/client";
import { FunctionComponent, useEffect, useState } from "react";
import { useLocation } from "react-router-dom";

import { Game, Section } from "../api";
import SectionPage from "../components/Section";

interface LocationState {
  storyId: number;
}

const START_GAME = gql`
  mutation startGame($id: BigInteger!) {
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

const TURN_TO = gql`
  mutation turnTo($id: Int!) {
    turnTo(index: $id) {
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

export const GameView: FunctionComponent<{}> = () => {
  const location = useLocation<LocationState>();
  const [section, setSection] = useState<Section | null>(null);

  const [start, response] =
    useMutation<{ startGame: Game }, { id: number }>(START_GAME);

  const [turnTo, toResponse] =
    useMutation<{ turnTo: Game }, { id: number }>(TURN_TO);

  useEffect(() => {
    if (location.state) {
      start({ variables: { id: location.state.storyId } });
    }
  }, [location.state, start]);

  useEffect(() => {
    if (toResponse.data) {
      setSection(toResponse.data.turnTo.section);
    }
    if (response.data && !section) {
      setSection(response.data.startGame.section);
    }
  }, [response.data, toResponse.data, section]);

  const handleTurnTo = (index: number) => {
    turnTo({ variables: { id: index } });
  };

  if (response.loading || !response.data) {
    return <span>LOADING</span>;
  }

  return (
    <SectionPage
      section={section}
      storyId={location.state.storyId}
      onTurnTo={handleTurnTo}
    />
  );
};
