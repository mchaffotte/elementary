import { useQuery, gql } from "@apollo/client";
import {
  IconButton,
  List,
  ListItem,
  ListItemSecondaryAction,
  ListItemText,
} from "@material-ui/core";
import PlayArrowIcon from "@material-ui/icons/PlayArrow";
import { FunctionComponent } from "react";

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
    return null;
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
