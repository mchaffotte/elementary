import { FunctionComponent } from "react";
import { Link } from "react-router-dom";
import { useQuery, gql } from "@apollo/client";

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

const Stories: FunctionComponent<{}> = () => {
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
    <div>
      <h1>Stories</h1>
      {data.stories.map((story) => (
        <div>
          <span>{story.name}</span>
          <Link to={{ pathname: "/game", state: { storyId: story.id } }}>
            New game
          </Link>
        </div>
      ))}
    </div>
  );
};

export default Stories;
