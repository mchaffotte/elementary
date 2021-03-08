import { FunctionComponent, useState, useEffect } from "react";
import { useLocation } from "react-router-dom";
import { useMutation, gql } from "@apollo/client";
import ReactMarkdown from "react-markdown";

import { Game } from "../api";

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
          id
        }
      }
    }
  }
`;

const TURN_TO = gql`
  mutation turnTo($id: Int!) {
    turnTo(nextReference: $id) {
      section {
        reference
        text
        actions {
          id
          description
        }
      }
    }
  }
`;

const STOP_GAME = gql`
  mutation stopGame {
    stopGame
  }
`;

const getRenderers = (storyId: number) => {
  return {
    image: ({
      alt,
      src,
      title,
    }: {
      alt?: string;
      src?: string;
      title?: string;
    }) => {
      return (
        <img
          alt={alt}
          src={`http://localhost:8181/stories/${storyId}/images/${src}`}
          title={title}
        />
      );
    },
  };
};

const GamePage: FunctionComponent<{}> = () => {
  const [sectionId, setSectionId] = useState(-1);
  const location = useLocation<LocationState>();

  const [start, response] = useMutation<{ startGame: Game }, { id: number }>(
    START_GAME
  );

  const [turnTo, toResponse] = useMutation<{ turnTo: Game }, { id: number }>(
    TURN_TO
  );

  const [stop] = useMutation<{ stopGame: boolean }>(STOP_GAME);

  useEffect(() => {
    if (location.state) {
      start({ variables: { id: location.state.storyId } });
    }
  }, [location.state, start]);

  useEffect(() => {
    if (sectionId !== -1) {
      turnTo({ variables: { id: sectionId } });
    }
  }, [sectionId, turnTo]);

  const handleTurnTo = (nextReference: number) => {
    setSectionId(nextReference);
  };

  let section;
  if (sectionId === -1) {
    if (!location) {
      return null;
    }
    if (response.loading || !response.data) {
      return <span>LOADING</span>;
    }
    section = response.data.startGame.section;
  } else {
    if (toResponse.loading || !toResponse.data) {
      return <span>LOADING</span>;
    }
    section = toResponse.data.turnTo.section;
  }

  return (
    <div>
      <span>{section.reference}</span>
      <ReactMarkdown
        source={section.text}
        renderers={getRenderers(location.state.storyId)}
      />
      <div>
        {section.actions.map((action) => (
          <div>
            {action.description}
            <button onClick={() => handleTurnTo(action.id)}>Next</button>
          </div>
        ))}
      </div>
    </div>
  );
};

export default GamePage;
