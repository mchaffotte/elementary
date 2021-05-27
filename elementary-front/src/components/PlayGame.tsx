import { useMutation, gql } from "@apollo/client";
import { FunctionComponent, useEffect, useState } from "react";

import { Game, Section } from "../api";
import { SectionView as SectionPage } from "../components/Section";

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

type PlayGameProps = {
  initialSection: Section | null;
  storyId: number;
};

export const PlayGame: FunctionComponent<PlayGameProps> = ({
  initialSection,
  storyId,
}) => {
  const [section, setSection] = useState<Section | null>(null);

  const [turnTo, response] =
    useMutation<{ turnTo: Game }, { id: number }>(TURN_TO);

  useEffect(() => {
    if (initialSection) {
      setSection(initialSection);
    }
  }, [initialSection]);

  useEffect(() => {
    if (response.data) {
      setSection(response.data.turnTo.section);
    }
  }, [response]);

  const handleTurnTo = (index: number) => {
    turnTo({ variables: { id: index } });
  };

  return (
    <SectionPage section={section} storyId={storyId} onTurnTo={handleTurnTo} />
  );
};
