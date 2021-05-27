import { FunctionComponent } from "react";
import { Switch, Route } from "react-router-dom";

import { Home } from "../pages/Home";
import { Game } from "../pages/Game";
import { NewGame } from "../pages/NewGame";
import { NotFound } from "../pages/NotFound";

export const Routes: FunctionComponent<{}> = () => {
  return (
    <Switch>
      <Route exact path="/" component={Home} />
      <Route exact path="/game" component={Game} />
      <Route exact path="/new-game" component={NewGame} />
      <Route path="*">
        <NotFound />
      </Route>
    </Switch>
  );
};
