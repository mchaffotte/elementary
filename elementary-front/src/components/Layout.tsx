import { Container, Grid } from "@mui/material";
import { FunctionComponent } from "react";

export const Layout: FunctionComponent<{}> = ({ children }) => {
  return (
    <Container maxWidth="sm">
      <Grid item>{children}</Grid>
    </Container>
  );
};
