import { useQuery, gql } from "@apollo/client";
import {
  IconButton,
  List,
  ListItem,
  ListItemSecondaryAction,
  ListItemText,
} from "@material-ui/core";
import PlayArrowIcon from "@material-ui/icons/PlayArrow";
import Skeleton from "@material-ui/lab/Skeleton";
import { FunctionComponent } from "react";

import { Story } from "../api";

type StoryData = {
  stories: Story[];
};

const GET_STORIES = gql`
  query getStories($offset: Int!, $limit: Int!) {
    stories(offset: $offset, limit: $limit) {
      id
      name
    }
  }
`;

type StoryListProps = {
  onChooseStory: Function;
};

export const StoryList: FunctionComponent<StoryListProps> = ({
  onChooseStory,
}) => {
  const { loading, data } = useQuery<StoryData, {}>(GET_STORIES, {
    variables: {
      offset: 0,
      limit: 10,
    },
  });

  if (loading || !data) {
    return (
      <List>
        {[1, 2, 3, 4].map((story) => (
          <ListItem key={story}>
            <Skeleton variant="text" width="80%" />
          </ListItem>
        ))}
      </List>
    );
  }

  return (
    <List>
      {data.stories.map((story) => (
        <ListItem key={story.id}>
          <ListItemText primary={story.name} />
          <ListItemSecondaryAction>
            <IconButton
              edge="end"
              aria-label="play"
              onClick={() => onChooseStory(story.id)}
            >
              <PlayArrowIcon />
            </IconButton>
          </ListItemSecondaryAction>
        </ListItem>
      ))}
    </List>
  );
};
