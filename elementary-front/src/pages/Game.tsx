import { FunctionComponent } from "react";
import { useLocation } from "react-router-dom";

import { StartGame } from "../components/StartGame";

interface LocationState {
  storyId: number;
}

export const Game: FunctionComponent<{}> = () => {
  const location = useLocation<LocationState>();

  return <StartGame storyId={location.state.storyId} />;
};
