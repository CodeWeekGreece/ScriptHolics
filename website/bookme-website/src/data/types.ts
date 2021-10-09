export type Owner = {
  name: string;
  phone: string;
};

export type Slot = {
  available: boolean;
  endTime: string;
  owner: Owner | null;
  startTime: string;
  uid: string;
};

export type ShopType = {
  title: string;
  imageUrl: string;
  description: string;
  slots: Slot;
};

export type User = {
  id: string;
  category: string;
};
