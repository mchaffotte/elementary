import { useQuery, gql } from "@apollo/client";
import { FunctionComponent } from "react";
import { Link } from "react-router-dom";

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

export const Home: FunctionComponent<{}> = () => {
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
        <div key={story.id}>
          <span>{story.name}</span>
          <Link to={{ pathname: "/game", state: { storyId: story.id } }}>
            New game
          </Link>
        </div>
      ))}
    </div>
  );
};
