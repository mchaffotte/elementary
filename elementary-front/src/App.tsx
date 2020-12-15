import { BrowserRouter as Router, Switch, Route } from "react-router-dom";
import { ApolloClient, InMemoryCache, ApolloProvider } from "@apollo/client";

import Game from "./components/Game";
import Stories from "./components/Stories";

const client = new ApolloClient({
  uri: "http://localhost:8181/graphql",
  cache: new InMemoryCache(),
});

function App() {
  return (
    <ApolloProvider client={client}>
      <Router>
        <Switch>
          <Route path="/game" children={<Game />} />
          <Route path="/" children={<Stories />} />
        </Switch>
      </Router>
    </ApolloProvider>
  );
}

export default App;
