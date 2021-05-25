import { CardMedia, Typography, makeStyles } from "@material-ui/core";
import {
  BaseCSSProperties,
  ClassNameMap,
} from "@material-ui/core/styles/withStyles";
import ReactMarkdown from "markdown-to-jsx";
import { FunctionComponent } from "react";

interface StyleProps {
  media: BaseCSSProperties;
}

type SectionTextProps = {
  text: string;
  storyId: number;
};

type ImageProps = {
  storyId: number;
  src: string;
  className: string;
};

const useStyles = makeStyles<StyleProps>({
  media: {
    maxHeight: 500,
    maxWidth: 518,
  },
});

const Image = ({ storyId, src, className }: ImageProps) => {
  return (
    <CardMedia
      component="img"
      className={className}
      image={`http://localhost:8181/stories/${storyId}/images/${src}`}
    />
  );
};

const options = (storyId: number, classes: ClassNameMap<string>) => {
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
          className: classes.media,
        },
      },
    },
  };
};

const SectionText: FunctionComponent<SectionTextProps> = ({
  text,
  storyId,
}) => {
  const classes = useStyles();

  return (
    <ReactMarkdown options={options(storyId, classes)}>{text}</ReactMarkdown>
  );
};

export default SectionText;
