import { FunctionComponent } from "react";
import { Switch, Route } from "react-router-dom";

import { Home } from "../pages/Home";
import { GameView as Game } from "../pages/Game";
import { NotFound } from "../pages/NotFound";

export const Routes: FunctionComponent<{}> = () => {
  return (
    <Switch>
      <Route exact path="/" component={Home} />
      <Route exact path="/game" component={Game} />
      <Route path="*">
        <NotFound />
      </Route>
    </Switch>
  );
};
