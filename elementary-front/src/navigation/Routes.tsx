import { Switch, Route } from "react-router-dom";

import Game from "../components/Game";
import Stories from "../components/Stories";
import { NotFound } from "../pages/NotFound";

export const Routes = () => {
  return (
    <Switch>
      <Route exact path="/" component={Stories} />
      <Route exact path="/game" component={Game} />
      <Route path="*">
        <NotFound />
      </Route>
    </Switch>
  );
};
