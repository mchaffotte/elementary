import { CardMedia, Typography } from "@mui/material";
import ReactMarkdown from "markdown-to-jsx";
import { FunctionComponent } from "react";

type SectionTextProps = {
  text: string;
  storyId: number;
};

type ImageProps = {
  storyId: number;
  src: string;
  className: string;
};

const Image = ({ storyId, src, className }: ImageProps) => {
  return (
    <CardMedia
      component="img"
      sx={{
        maxHeight: 500,
        maxWidth: 518,
      }}
      image={`http://localhost:8181/stories/${storyId}/images/${src}`}
    />
  );
};

const options = (storyId: number) => {
  return {
    overrides: {
      p: {
        component: Typography,
        props: { paragraph: true, align: "justify" },
      },
      span: {
        component: Typography,
        props: { paragraph: true, align: "justify" },
      },
      img: {
        component: Image,
        props: {
          storyId,
        },
      },
    },
  };
};

export const SectionText: FunctionComponent<SectionTextProps> = ({
  text,
  storyId,
}) => {
  return <ReactMarkdown options={options(storyId)}>{text}</ReactMarkdown>;
};
