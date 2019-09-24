export abstract class Entity {

  public name: string;

  constructor() {}

  toString(): string {
    return String(this.name);
  }
}
