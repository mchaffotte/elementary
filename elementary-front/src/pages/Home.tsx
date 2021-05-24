import {
  Container,
  CssBaseline,
  Grid,
  Typography,
  makeStyles,
} from "@material-ui/core";
import { FunctionComponent } from "react";
import { useHistory } from "react-router-dom";

import { StoryList } from "../components/StoriesList";

const useStyles = makeStyles((theme) => ({
  title: {
    margin: theme.spacing(4, 0, 2),
  },
}));

export const Home: FunctionComponent<{}> = () => {
  const classes = useStyles();
  const history = useHistory();

  const handleChooseStory = (storyId: number) => {
    history.push({
      pathname: "/game",
      state: { storyId },
    });
  };

  return (
    <>
      <CssBaseline />
      <Container maxWidth="sm">
        <Grid item xs={12} md={6}>
          <Typography variant="h6" className={classes.title}>
            Choose the story
          </Typography>
          <StoryList onChooseStory={handleChooseStory} />
        </Grid>
      </Container>
    </>
  );
};
