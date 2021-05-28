import {
  Container,
  CssBaseline,
  Grid,
  Button,
  Card,
  makeStyles,
} from "@material-ui/core";
import { FunctionComponent } from "react";
import { Link } from "react-router-dom";

const useStyles = makeStyles({
  root: {
    minWidth: 275,
  },
});

export const Home: FunctionComponent<{}> = () => {
  const classes = useStyles();

  return (
    <>
      <CssBaseline />
      <Container maxWidth="sm">
        <Grid item xs={12} md={6}>
          <Card className={classes.root} variant="outlined">
            <Button
              variant="contained"
              color="primary"
              component={Link}
              to="/game"
            >
              Continue
            </Button>
            <Button
              variant="contained"
              color="primary"
              component={Link}
              to="/new-game"
            >
              New Game
            </Button>
          </Card>
        </Grid>
      </Container>
    </>
  );
};
