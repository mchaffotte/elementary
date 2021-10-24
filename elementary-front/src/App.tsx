import { ApolloClient, InMemoryCache, ApolloProvider } from "@apollo/client";
import {
  ThemeProvider,
  StyledEngineProvider,
  createTheme,
} from "@mui/material/styles";
import { BrowserRouter as Router } from "react-router-dom";

import { Routes } from "./navigation/Routes";

const client = new ApolloClient({
  uri: "http://localhost:8181/graphql",
  cache: new InMemoryCache(),
});

const theme = createTheme();

const App = () => {
  return (
    <ApolloProvider client={client}>
      <StyledEngineProvider injectFirst>
        <ThemeProvider theme={theme}>
          <Router>
            <Routes />
          </Router>
        </ThemeProvider>
      </StyledEngineProvider>
    </ApolloProvider>
  );
};

export default App;
