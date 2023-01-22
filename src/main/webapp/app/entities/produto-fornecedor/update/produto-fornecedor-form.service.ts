import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IProdutoFornecedor, NewProdutoFornecedor } from '../produto-fornecedor.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IProdutoFornecedor for edit and NewProdutoFornecedorFormGroupInput for create.
 */
type ProdutoFornecedorFormGroupInput = IProdutoFornecedor | PartialWithRequiredKeyOf<NewProdutoFornecedor>;

type ProdutoFornecedorFormDefaults = Pick<NewProdutoFornecedor, 'id'>;

type ProdutoFornecedorFormGroupContent = {
  id: FormControl<IProdutoFornecedor['id'] | NewProdutoFornecedor['id']>;
  valor: FormControl<IProdutoFornecedor['valor']>;
  observacoes: FormControl<IProdutoFornecedor['observacoes']>;
  produto: FormControl<IProdutoFornecedor['produto']>;
  fornecedor: FormControl<IProdutoFornecedor['fornecedor']>;
  produtoFornecedor: FormControl<IProdutoFornecedor['produtoFornecedor']>;
};

export type ProdutoFornecedorFormGroup = FormGroup<ProdutoFornecedorFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ProdutoFornecedorFormService {
  createProdutoFornecedorFormGroup(produtoFornecedor: ProdutoFornecedorFormGroupInput = { id: null }): ProdutoFornecedorFormGroup {
    const produtoFornecedorRawValue = {
      ...this.getFormDefaults(),
      ...produtoFornecedor,
    };
    return new FormGroup<ProdutoFornecedorFormGroupContent>({
      id: new FormControl(
        { value: produtoFornecedorRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      valor: new FormControl(produtoFornecedorRawValue.valor, {
        validators: [Validators.required],
      }),
      observacoes: new FormControl(produtoFornecedorRawValue.observacoes),
      produto: new FormControl(produtoFornecedorRawValue.produto),
      fornecedor: new FormControl(produtoFornecedorRawValue.fornecedor),
      produtoFornecedor: new FormControl(produtoFornecedorRawValue.produtoFornecedor),
    });
  }

  getProdutoFornecedor(form: ProdutoFornecedorFormGroup): IProdutoFornecedor | NewProdutoFornecedor {
    return form.getRawValue() as IProdutoFornecedor | NewProdutoFornecedor;
  }

  resetForm(form: ProdutoFornecedorFormGroup, produtoFornecedor: ProdutoFornecedorFormGroupInput): void {
    const produtoFornecedorRawValue = { ...this.getFormDefaults(), ...produtoFornecedor };
    form.reset(
      {
        ...produtoFornecedorRawValue,
        id: { value: produtoFornecedorRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): ProdutoFornecedorFormDefaults {
    return {
      id: null,
    };
  }
}
