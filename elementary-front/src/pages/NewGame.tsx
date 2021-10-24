import { Card, CardContent, Typography } from "@mui/material";
import { FunctionComponent } from "react";
import { useHistory } from "react-router-dom";

import { Layout } from "../components/Layout";
import { StoryList } from "../components/StoryList";

export const NewGame: FunctionComponent<{}> = () => {
  const history = useHistory();

  const handleChooseStory = (storyId: number) => {
    history.push({
      pathname: "/game",
      state: { storyId },
    });
  };

  return (
    <Layout>
      <Card variant="outlined">
        <CardContent>
          <Typography gutterBottom variant="h5" component="h2">
            Choose the story
          </Typography>
          <StoryList onChooseStory={handleChooseStory} />
        </CardContent>
      </Card>
    </Layout>
  );
};
