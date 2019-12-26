export class Color {
  private id: number;
  color: string;
  category: string;
  type: string;
  code: {
    rgba: number[],
    hex: string
  };

  constructor(id: number) {
    this.id = id;
    this.color = "newcolor";
    this.category = "newCategory";
    this.type = "newType";
    this.code = {
      rgba: [255, 255, 255, 255],
      hex: "#FFF"
    };
  }
}
