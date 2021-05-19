import { useQuery, gql } from "@apollo/client";
import {
  Container,
  CssBaseline,
  Grid,
  IconButton,
  List,
  ListItem,
  ListItemSecondaryAction,
  ListItemText,
  Typography,
  makeStyles,
} from "@material-ui/core";
import PlayArrowIcon from "@material-ui/icons/PlayArrow";
import { FunctionComponent } from "react";
import { useHistory } from "react-router-dom";

import { Story } from "../api";

interface StoryData {
  stories: Story[];
}

const GET_STORIES = gql`
  query getStories($offset: Int!, $limit: Int!) {
    stories(offset: $offset, limit: $limit) {
      id
      name
    }
  }
`;

const useStyles = makeStyles((theme) => ({
  title: {
    margin: theme.spacing(4, 0, 2),
  },
}));

export const Home: FunctionComponent<{}> = () => {
  const classes = useStyles();
  const history = useHistory();
  const { loading, data } = useQuery<StoryData, {}>(GET_STORIES, {
    variables: {
      offset: 0,
      limit: 10,
    },
  });

  if (loading || !data) {
    return <h1>Stories</h1>;
  }

  return (
    <>
      <CssBaseline />
      <Container maxWidth="sm">
        <Grid item xs={12} md={6}>
          <Typography variant="h6" className={classes.title}>
            Choose the story
          </Typography>
          <List>
            {data.stories.map((story) => (
              <ListItem key={story.id}>
                <ListItemText primary={story.name} />
                <ListItemSecondaryAction>
                  <IconButton
                    edge="end"
                    aria-label="play"
                    onClick={() =>
                      history.push({
                        pathname: "/game",
                        state: { storyId: story.id },
                      })
                    }
                  >
                    <PlayArrowIcon />
                  </IconButton>
                </ListItemSecondaryAction>
              </ListItem>
            ))}
          </List>
        </Grid>
      </Container>
    </>
  );
};
