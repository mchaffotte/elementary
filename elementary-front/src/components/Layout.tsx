import { Container, CssBaseline, Grid } from "@material-ui/core";
import { FunctionComponent } from "react";

export const Layout: FunctionComponent<{}> = ({ children }) => {
  return (
    <>
      <CssBaseline />
      <Container maxWidth="sm">
        <Grid item>{children}</Grid>
      </Container>
    </>
  );
};
