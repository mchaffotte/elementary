import { FunctionComponent } from "react";
import { useLocation, useHistory } from "react-router-dom";

import { ContinueGame } from "../components/ContinueGame";
import { StartGame } from "../components/StartGame";

type LocationState = {
  storyId: number;
};

export const Game: FunctionComponent<{}> = () => {
  const location = useLocation<LocationState>();
  const history = useHistory();

  const handleStart = () => {
    history.replace({ ...history.location, state: undefined });
  };

  if (location.state) {
    return <StartGame storyId={location.state.storyId} onStart={handleStart} />;
  }
  return <ContinueGame />;
};
